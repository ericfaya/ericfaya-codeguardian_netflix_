import os
from github import Github

# Autenticación con token personalizado
token = os.environ["TOKEN_GITHUB"]
gh = Github(token)

# Obtener nombre completo del repo dinámicamente (ej: "ericfaya/mi-proyecto")
repo_name = os.environ["GITHUB_REPOSITORY"]
repo = gh.get_repo(repo_name)

# Lógica simple: revisar PRs abiertas y asignar un revisor si hay >=5 commits
for pr in repo.get_pulls(state="open"):
    if pr.commits >= 5 and not pr.requested_reviewers:
        print(f"PR #{pr.number} tiene {pr.commits} commits y no tiene revisores asignados.")

        # 🔁 Aquí puedes implementar lógica avanzada de afinidad
        # Por ahora, seleccionamos un revisor fijo como ejemplo
        best_reviewer = "ericfaya"  # 🧠 Reemplazar luego por lógica real

        try:
            pr.create_review_request([best_reviewer])
            print(f"✅ Revisor '{best_reviewer}' asignado a PR #{pr.number}")
        except Exception as e:
            print(f"⚠️ Error al asignar revisor: {e}")
    else:
        print(f"PR #{pr.number} ya tiene revisores o no cumple condiciones.")