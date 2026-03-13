#!/usr/bin/env bash
# save-plan-report.sh — ExitPlanMode 시 플랜 파일을 .person/reports/에 자동 저장
# matcher: "ExitPlanMode" (settings.local.json PostToolUse)

PROJECT_DIR="${CLAUDE_PROJECT_DIR:-$(pwd)}"
INPUT=$(cat)  # stdin 소비 (무시)

# Python 찾기 (log-activity.sh 동일 패턴)
PYTHON=""
for cmd in python3 python; do
  if command -v "$cmd" >/dev/null 2>&1 && "$cmd" --version >/dev/null 2>&1; then
    PYTHON="$cmd"
    break
  fi
done
[ -z "${PYTHON}" ] && exit 0

# PROJECT_DIR을 환경변수로 전달, 'PYEOF' 인용으로 bash 이스케이프 처리 방지
REPORT_PROJECT_DIR="${PROJECT_DIR}" "${PYTHON}" - <<'PYEOF'
import re, os, sys
from datetime import datetime
from pathlib import Path

plans_dir = Path.home() / '.claude' / 'plans'
reports_base = Path(os.environ['REPORT_PROJECT_DIR']) / '.person' / 'reports'

plan_files = list(plans_dir.glob('*.md'))
if not plan_files:
    sys.exit(0)

plan_file = max(plan_files, key=lambda p: p.stat().st_mtime)
content = plan_file.read_text(encoding='utf-8')

title_match = re.search(r'^#\s+(.+)$', content, re.MULTILINE)
if not title_match:
    sys.exit(0)

title = title_match.group(1).strip()
slug = re.sub(r'[<>:"|?*/\\]', '', title)
slug = re.sub(r'[\s_]+', '-', slug).strip('-')

date = datetime.now().strftime('%Y-%m-%d')
reports_dir = reports_base / date
reports_dir.mkdir(parents=True, exist_ok=True)

report_path = reports_dir / f'{slug}-report.md'
counter = 1
while report_path.exists():
    report_path = reports_dir / f'{slug}-{counter}-report.md'
    counter += 1

report_path.write_text(content, encoding='utf-8')
print(f"Plan report saved: {report_path}", file=sys.stderr)
PYEOF

exit 0
