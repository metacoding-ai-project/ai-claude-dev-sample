#!/usr/bin/env bash
# git-commit.sh — git 플래그 생성 + 자동 커밋 통합 스크립트
# 사용법:
#   bash git-commit.sh create-flag  (ExitPlanMode 시 — 플래그 생성)
#   bash git-commit.sh commit        (Stop 시 — 플래그 확인 후 커밋)

MODE="$1"
PROJECT_DIR="${CLAUDE_PROJECT_DIR:-$(pwd)}"
FLAG_FILE="$PROJECT_DIR/.claude/.plan-commit-pending"

if [ "$MODE" = "create-flag" ]; then
  # ── ExitPlanMode: 최신 plan 파일 제목으로 플래그 생성 ──
  INPUT=$(cat)

  PYTHON=""
  for cmd in python3 python; do
    if command -v "$cmd" >/dev/null 2>&1; then
      PYTHON="$cmd"; break
    fi
  done
  [ -z "$PYTHON" ] && exit 0

  FLAG_FILE="$FLAG_FILE" "$PYTHON" - <<PYEOF
import re, sys, os
from pathlib import Path
from datetime import datetime

plans_dir = Path.home() / '.claude' / 'plans'
plan_files = list(plans_dir.glob('*.md'))
if not plan_files:
    sys.exit(0)

plan_file = max(plan_files, key=lambda p: p.stat().st_mtime)
content = plan_file.read_text(encoding='utf-8')

m = re.search(r'^#\s+(.+)$', content, re.MULTILINE)
title = m.group(1).strip() if m else f'plan-{datetime.now().strftime("%H%M%S")}'

flag = Path(os.environ['FLAG_FILE'])
flag.parent.mkdir(parents=True, exist_ok=True)
flag.write_text(title, encoding='utf-8')
print(f"Plan flag set: {title}", file=sys.stderr)
PYEOF

elif [ "$MODE" = "commit" ]; then
  # ── Stop: 플래그 있으면 git add + commit + 플래그 삭제 ──
  INPUT=$(cat)

  # 플래그 없으면 종료 (plan 실행 중 아님)
  [ ! -f "$FLAG_FILE" ] && exit 0

  # git 변경사항 확인
  cd "$PROJECT_DIR" || exit 0
  CHANGES=$(git status --porcelain 2>/dev/null)
  [ -z "$CHANGES" ] && exit 0

  # 플랜 제목 읽기
  TITLE=$(cat "$FLAG_FILE" 2>/dev/null || echo "plan implementation")

  # 커밋
  git add .
  git commit -m "[plan] $TITLE

Co-Authored-By: Claude Sonnet 4.6 <noreply@anthropic.com>" 2>/dev/null

  # 성공 시 플래그 삭제
  if [ $? -eq 0 ]; then
    rm -f "$FLAG_FILE"
    echo "Auto-committed: [plan] $TITLE" >&2
  fi

fi

exit 0
