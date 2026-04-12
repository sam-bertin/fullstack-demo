# Configuration Protection de Branche - GitHub (Implémenté)

**Clé Jira** : A.3.3 (DoD CI/CD - Phase 1)  
**Date** : 2026-04-10  
**Statut** : Implémenté et actif

---

## Vue d'ensemble

La protection de branche `main` est configurée, active et versionnée.

Le cycle CI backend/frontend est maintenant en place (B.3.1 + B.3.2) et ses checks requis sont activés dans `branch-ruleset.json` (B.3.3).

---

## Configuration appliquée

### Fichier
`branch-ruleset.json`

### Cible
Branche `main` (`~DEFAULT_BRANCH`)

---

## Règles actuellement actives

### 1. Immuabilité

| Règle | Statut | Effet |
|-------|--------|-------|
| Suppression de branche interdite | ✅ Actif | Impossible de supprimer `main` |
| Push --force autorisé | ✅ Actif | Permis pour rebases manuels |

### 2. Pull Request obligatoire

| Règle | Statut | Effet |
|-------|--------|-------|
| Commit direct interdit | ✅ Actif | Tous les changements passent par PR |
| Minimum 1 approbateur | ✅ Actif | Self-review req (tu approuves toi-même) |
| Review périmée non dismissée | ✅ Actif | Les reviews restent valides après commits |
| Threads résolus obligatoire | ✅ Actif | Tous les commentaires doivent être résolus avant merge |

---

## Cycle d'une PR

1. **Developer crée branche** depuis `main` (ex: `feature/foo`)
2. **Pousse commits** et ouvre PR vers `main`
3. ✅ **PR créée** (pas de blocage immédiat)
4. 👤 **Self-review** : Tu approuves ta propre PR
5. 💬 **Résous threads** : Si des commentaires auto-générés, resolve-les
6. ✅ **Merge autorisé** si :
   - PR a ≥ 1 approbation ✅
   - Tous les threads sont résolus ✅
   - No conflicts avec `main` ✅

---

## Différence avant / après

| Avant (aucune protection) | Maintenant |
|---|---|
| Commit direct sur `main` : ❌ Possible | Commit direct : **Impossible** |
| Push --force : ✅ Libre | Push --force : ✅ Libre (rebases ok) |
| Merge sans review : ✅ Possible | Merge sans review : **Impossible** |
| Suppression `main` : ✅ Possible | Suppression : **Impossible** |

---

## Checks requis actifs (B.3.3)

Les workflows backend/frontend sont maintenant **requis** comme status checks :

```json
{
  "type": "required_status_checks",
  "parameters": {
    "required_status_checks": [
      { "context": "CI Backend / Lint (Checkstyle)" },
      { "context": "CI Backend / Unit Tests (JUnit 5)" },
      { "context": "CI Backend / Build (Maven)" },
      { "context": "CI Frontend / Lint (ESLint)" },
      { "context": "CI Frontend / Unit Tests (Vitest)" },
      { "context": "CI Frontend / Build (Vite)" }
    ]
  }
}
```

À ce moment, la branche protection sera **stricte** : merge bloqué si un check est ❌.

---

## Comment tester ou modifier

### Consulter la config actuelle
GitHub UI → Settings → Rules → Voir `Protection de la branche Main`

### Modifier la config
Editer `branch-ruleset.json` dans le repository puis ouvrir une PR.

### Contourner temporairement (si besoin)
Impossible sans modifier la règle ou utiliser `git push --force` (qui est autorisé pour rebases)

---

## Vérification manuelle

### Test 1 : Interdiction commit direct
```bash
git checkout main
echo "test" > test.txt
git add test.txt
git commit -m "Direct commit"
git push origin main
```
**Résultat attendu** : ❌ Push rejeté ("protected branch")

### Test 2 : PR + self-review + merge
```bash
git checkout -b feature/test
echo "feature" > feature.txt
git add feature.txt
git commit -m "feat: test feature"
git push origin feature/test
# Créer PR via GitHub UI
# Self-approuver via GitHub UI
# Merge via GitHub UI
```
**Résultat attendu** : ✅ Merge autorisé

---

## Limitations actuelles

1. **Politique CI push-only**
   → Les checks sont produits sur les commits pushes, puis reutilises dans la PR.
   → Cette politique est volontairement alignee avec les workflows actuels.

2. **Reviews périmées ne sont jamais dismissées**
   → Ancienne review reste valide même après nouveau commit
   → Normal pour solo dev, peut être revu si collaboration future

3. **1 approbateur requis** (c'est toi)
   → Pas de blocage externe
   → À revoir si collaboration future

---

## Roadmap

| Étape | Jira | Statut | Description |
|-------|------|--------|-------------|
| Phase 1 | A.3.3 | ✅ Terminé | Protection branche (suppression, PR obligatoire, review, threads) |
| Phase 2 | B.3.1/B.3.2/B.3.3 | ✅ Terminé | Workflows CI + required status checks |

---

## Jira-ready comment

```
A.3.3 Phase 1 - Branch Protection Implémenté

Réalisé:
• Protection branche main: suppression interdite, PR obligatoire, 1 review min
• Threads résolus requis avant merge
• Push --force autorisé (rebases)
• 6 status checks requis (backend + frontend)

État:
✅ branch-ruleset.json déployé et actif
✅ Documenté dans docs/04_devops/GitHub_Branch_Protection_Setup.md

Phase 2 (B.3.1/B.3.2/B.3.3):
→ Required status checks activés et versionnés dans `branch-ruleset.json`
→ Merge bloqué si lint/test/build backend ou frontend échouent
```
