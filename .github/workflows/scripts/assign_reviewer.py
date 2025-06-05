import os
import json
from github import Github
from collections import defaultdict
from difflib import SequenceMatcher

# Cargar contexto histórico
with open("user_contexts.json") as f:
    user_contexts = json.load(f)

# Conectar con GitHub
token = os.environ["TOKEN_GITHUB"]
repo_name = os.environ["GITHUB_REPOSITORY"]
gh = Github(token)
repo = gh.get_repo(repo_name)

def vector_from_files(file_paths):
    vector = defaultdict(int)
    for file in file_paths:
        parts = file.filename.split("/")
        if len(parts) < 2:
            continue
        package = ".".join(parts[:-1])
        filename = parts[-1]
        ext = filename.split(".")[-1]
        vector[f"file:{filename}"] += 1
        vector[f"package:{package}"] += 1
        vector[f"type:{ext}"] += 1
    return dict(vector)

def similarity(vec1, vec2):
    common_keys = set(vec1.keys()) & set(vec2.keys())
    if not common_keys:
        return 0
    score = sum(min(vec1[k], vec2[k]) for k in common_keys)
    return score

# Evaluar PRs abiertas
for pr in repo.get_pulls(state="open"):
    if pr.commits < 5 or pr.requested_reviewers:
        continue

    print(f"Evaluando PR #{pr.number} con {pr.commits} commits...")

    # Vector semántico de la PR (archivos modificados)
    files = pr.get_files()
    pr_vector = vector_from_files(files)

    # Calcular afinidad con cada usuario
    best_user = None
    best_score = -1

    for user, context in user_contexts.items():
        score = similarity(pr_vector, context)
        print(f"  Afinidad con {user}: {score}")
        if score > best_score:
            best_score = score
            best_user = user

    if best_user:
        try:
            pr.create_review_request([best_user])
            print(f"✅ Asignado revisor automático: {best_user} con afinidad {best_score}")
        except Exception as e:
            print(f"⚠️ Error al asignar a {best_user}: {e}")
