#!/usr/bin/env bash
# skill-agent-log.sh — Agent/Skill 호출 기록
# matcher: "Agent|Skill" (settings.local.json)

PROJECT_DIR="${CLAUDE_PROJECT_DIR:-$(pwd)}"
LOG_FILE="${PROJECT_DIR}/.person/logs/activity.log"
mkdir -p "$(dirname "${LOG_FILE}")"

INPUT=$(cat)
[ -z "${INPUT}" ] && exit 0

# Windows: python3이 MS Store alias일 수 있으므로 실제 Python을 찾는다
PYTHON=""
for cmd in python3 python; do
  if command -v "$cmd" >/dev/null 2>&1 && "$cmd" --version >/dev/null 2>&1; then
    PYTHON="$cmd"
    break
  fi
done

if command -v jq >/dev/null 2>&1; then
  ENTRY=$(echo "${INPUT}" | jq -c '
    (.tool_name // "") as $tool |
    if $tool == "Agent" then
      { timestamp: (now | todate), type: "agent", subagent: .tool_input.subagent_type, description: .tool_input.description }
    elif $tool == "Skill" then
      { timestamp: (now | todate), type: "skill", skill: .tool_input.skill, args: .tool_input.args }
      | with_entries(select(.value != null and .value != ""))
    else empty end
  ' 2>/dev/null) || ENTRY=""
elif [ -n "${PYTHON}" ]; then
  ENTRY=$(echo "${INPUT}" | "${PYTHON}" -c "
import sys, json
from datetime import datetime, timezone
try:
    data = json.load(sys.stdin)
except Exception:
    sys.exit(0)
tool = data.get('tool_name', '')
ti = data.get('tool_input') or {}
ts = datetime.now(timezone.utc).strftime('%Y-%m-%dT%H:%M:%SZ')
if tool == 'Agent':
    entry = {'timestamp': ts, 'type': 'agent', 'subagent': ti.get('subagent_type'), 'description': ti.get('description')}
elif tool == 'Skill':
    entry = {'timestamp': ts, 'type': 'skill', 'skill': ti.get('skill')}
    if ti.get('args'):
        entry['args'] = ti['args']
else:
    sys.exit(0)
entry = {k: v for k, v in entry.items() if v}
print(json.dumps(entry, ensure_ascii=False))
" 2>/dev/null) || ENTRY=""
else
  exit 0
fi

if [ -n "${ENTRY}" ]; then
  if [ -f "${LOG_FILE}" ]; then
    echo "${ENTRY}" | cat - "${LOG_FILE}" > "${LOG_FILE}.tmp" && mv "${LOG_FILE}.tmp" "${LOG_FILE}"
  else
    echo "${ENTRY}" > "${LOG_FILE}"
  fi
  # 20줄 초과 시 truncate
  if [ $(wc -l < "${LOG_FILE}") -gt 20 ]; then
    head -n 20 "${LOG_FILE}" > "${LOG_FILE}.tmp" && mv "${LOG_FILE}.tmp" "${LOG_FILE}"
  fi
fi
exit 0
