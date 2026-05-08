# Echoes of the Abyss

**Echoes of the Abyss** est un jeu 2D action rogue-lite developpe avec [libGDX](https://libgdx.com/). Le joueur explore un donjon mysterieux rempli d'ennemis, avec pour objectif de progresser etage apres etage tout en collectant des ressources et en renforcant son personnage.

## Jouer

### Web (navigateur)

Jouez directement sur [itch.io](https://itch.io/) — aucune installation requise ! Votre progression est sauvegardee automatiquement dans le navigateur (localStorage).

### Windows

1. Telechargez le ZIP depuis itch.io.
2. Extrayez le dossier quelque part sur votre PC. **Ne lancez pas le jeu depuis l'interieur du ZIP.**
3. Lancez le jeu en double-cliquant sur `launch_game.bat`.

### macOS

1. Telechargez le ZIP depuis itch.io.
2. Extrayez le dossier quelque part sur votre Mac.
3. Ouvrez un Terminal et executez :
   ```bash
   chmod +x launch_game.sh
   ./launch_game.sh
   ```
   Ou faites clic-droit sur `launch_game.sh` → Ouvrir avec → Terminal.

> Si macOS bloque l'application ("developpeur non identifie"), allez dans **Reglages Systeme → Confidentialite et securite** et cliquez sur **Ouvrir quand meme**.

### Linux

1. Telechargez le ZIP depuis itch.io.
2. Extrayez le dossier quelque part sur votre machine.
3. Ouvrez un terminal dans le dossier extrait et executez :
   ```bash
   chmod +x launch_game.sh
   ./launch_game.sh
   ```

### Prerequis (Windows / macOS / Linux)

- **Java 11 ou superieur** requis ([Adoptium Temurin 17](https://adoptium.net/) recommande)
- Si le jeu ne se lance pas ou affiche une erreur "class file version", votre Java est trop ancien.
- **Ne deplacez pas et ne renommez pas** `JavaProject.jar` ni le dossier `assets` — ils doivent rester ensemble.

#### Installation de Java (si necessaire)

```bash
# Windows (winget)
winget install EclipseAdoptium.Temurin.17.JDK

# Verifier l'installation
java -version
```

> Si `JAVA_HOME` n'est pas configure automatiquement :
> ```powershell
> [System.Environment]::SetEnvironmentVariable("JAVA_HOME", "C:\Program Files\Eclipse Adoptium\jdk-17.0.19.10-hotspot", "User")
> ```
> Puis relancez le terminal.

## Commandes de build (developpeurs)

### Jouer (Desktop)

```bash
./gradlew :lwjgl3:run
```

### Build Desktop (JAR executable)

```bash
./gradlew :lwjgl3:jar
# -> lwjgl3/build/libs/JavaProject.jar
```

### Build Web (HTML5 pour itch.io)

```bash
./gradlew :html:dist
# -> html/build/dist/ (dossier a uploader sur itch.io)
```

### Build Web + ZIP (pret a uploader)

```bash
./gradlew :html:distZip
# -> html/build/distributions/JavaProject-html.zip
```

### Creer les executables multi-plateforme

```bash
./gradlew createExecutables
# -> lwjgl3/dist/windows/, lwjgl3/dist/mac/, lwjgl3/dist/linux/
```

### Autres commandes utiles

| Commande | Description |
|---|---|
| `./gradlew clean` | Nettoie les fichiers de build |
| `./gradlew build` | Compile le projet complet |
| `./gradlew test` | Execute les tests unitaires |
| `./gradlew :lwjgl3:generateFonts` | Regenere les BitmapFonts (si modification de polices) |

## Deploiement sur itch.io

1. `./gradlew :html:dist`
2. Aller sur [itch.io](https://itch.io/) → "New project"
3. **Kind of project** : HTML
4. Uploader le contenu de `html/build/dist/` (ou le zip via `distZip`)
5. Cocher **"This file will be played in the browser"**
6. Dimensions recommandees : **960 x 540**
7. Activer le **bouton Fullscreen**

## Structure du projet

```
Echoes-of-the-abyss/
  core/          # Logique du jeu (entites, scenes, items, utils)
  lwjgl3/        # Backend desktop (LWJGL3)
  html/          # Backend web (GWT) pour itch.io
  assets/        # Sprites, sons, musiques, fonts
```

## Controles

| Action | Touche |
|---|---|
| Deplacement | WASD / Fleches |
| Saut | W / Espace |
| Attaque | Z / Clic gauche |
| Roulade | Shift |
| Potion | E |
| Pause | Echap |

Les touches sont reconfigurables dans le menu Options.

## A propos

- **Genre** : Rogue-lite, Beat 'em all, Action 2D
- **Plateformes** : PC (Windows, macOS, Linux) + Navigateur web
- **Style graphique** : Pixel art 2D
- **Objectif** : Progresser le plus loin possible dans le donjon, acheter des equipements et potions dans la boutique entre les runs
