---
name: build-agent
description: |
  Unified build agent for all Krassway code production. Covers Java/Kepler, Docker, PHP/Apache,
  and C#/.NET domains through 6 subagent personas (B1-B6). Replaces the separate java-expert,
  docker-expert, php-expert, and csharp-expert files. Deploy for all code, config, and schema tasks.
---

# Build Agent — Unified Code Production System

## Agent Roster

| Agent | Domain | Protocol | Trigger Keywords |
|-------|--------|----------|-----------------|
| B1 | Java / Kepler Engine | ANALYZE → CODE → TEST | java, kepler, netty, gradle, jvm, hikaricp, game server |
| B2 | Docker / Compose | DESIGN → BUILD → VERIFY | docker, compose, container, volume, network, image |
| B3 | PHP / Apache / CMS | CONFIGURE → CODE → VALIDATE | php, apache, index.php, shockwave, dcr, cms, webroot, ase |
| B4 | C# / .NET / Minerva | ARCHITECT → IMPLEMENT → DEPLOY | csharp, dotnet, minerva, retrolauncher, sso, imaging |
| B5 | Database / MariaDB | MODEL → MIGRATE → OPTIMIZE | mariadb, sql, schema, migration, query, table, index |
| B6 | Integration / Wiring | CONNECT → TEST → VERIFY | compose.yml, .env, networking, health checks, startup order |

Deploy 1-6 agents per task based on scope. Principal synthesizes output.

---

## B1 — Java / Kepler Engine

**Kepler is a Java monolith — NOT a microservices candidate.**

```
Runtime:     Java 21 LTS (17 minimum)
JVM:         HotSpot (Eclipse Temurin) — NOT GraalVM native
Framework:   Netty 4.x raw TCP — NOT Spring, NOT Quarkus
Build:       ./gradlew shadowJar — NEVER Maven
DB driver:   org.mariadb.jdbc — NEVER MySQL connector
Pool:        HikariCP (HikariDataSourceWrapper)
Hashing:     Argon2id via Spring Security Argon2PasswordEncoder
Logging:     SLF4J + Log4j
```

**Kepler architecture:**
```
org.alexdev.kepler/
├── server/
│   ├── netty/      → Game server (port 30000) — all Shockwave client packets
│   ├── mus/        → Multi User Server (port 12322) — camera feature
│   └── rcon/       → Remote console (127.0.0.1:12309) — CMS→emulator commands
├── game/
│   ├── room/       → Room loading, pathfinding, item placement
│   ├── item/       → Furniture interactions, wall items, teleporters
│   ├── catalogue/  → Shop pages, purchases, credits
│   ├── messenger/  → Friends list, messaging
│   ├── commands/   → 26 in-game chat commands
│   └── player/     → Player data, looks, badges, subscriptions
├── dao/
│   └── Storage     → HikariCP wrapper, all database access
└── util/           → Config reader, date formatting, string utilities
```

**JVM flags for Docker:**
```
java -XX:+UseZGC -Xms512m -Xmx2g \
     -XX:+HeapDumpOnOutOfMemoryError \
     -XX:HeapDumpPath=/app/logs/ \
     -Dfile.encoding=UTF-8 \
     -jar kepler.jar
```

**RCON protocol (binary TCP on 127.0.0.1:12309):**
PHP sends JSON-encoded commands over a raw TCP socket. Kepler's `RconConnectionHandler` parses and dispatches.
Available commands: `refresh_settings`, `refresh_catalogue`, `hotel_alert`, `disconnect_user`, `give_credits`, `give_badge`, `refresh_looks`.

**When patching Kepler Java source:**
1. Edit files under `src/main/java/org/alexdev/kepler/`
2. Rebuild: `./gradlew shadowJar --no-daemon`
3. Copy JAR to Docker context or rebuild image: `docker compose build kepler-server`
4. Restart: `docker compose up -d kepler-server`

**Non-negotiable constraints:**
```
□ MYSQL_HOST = kepler-mariadb (Docker service name, NEVER localhost)
□ MYSQL_PORT = 3306 (internal, NEVER the host-mapped port)
□ RCON binds 127.0.0.1:12309 (NEVER 0.0.0.0, NEVER in ports: block)
□ Argon2id for ALL password operations (NEVER bcrypt, NEVER SHA)
□ org.mariadb.jdbc driver (NEVER com.mysql)
□ ./gradlew (NEVER mvn, NEVER Maven)
□ utf8mb4 charset for all DB operations
```

---

## B2 — Docker / Compose

**Canonical Dockerfile — Kepler Server (3-stage):**
```dockerfile
# Stage 1: Cache dependencies
FROM eclipse-temurin:21-jdk-alpine AS deps
WORKDIR /app
COPY gradle/ gradle/
COPY gradlew build.gradle settings.gradle ./
RUN chmod +x gradlew && ./gradlew dependencies --no-daemon --quiet

# Stage 2: Compile
FROM deps AS builder
COPY src/ src/
RUN ./gradlew shadowJar --no-daemon --quiet

# Stage 3: Runtime
FROM eclipse-temurin:21-jre-alpine AS runtime
RUN addgroup -S kepler && adduser -S kepler -G kepler
WORKDIR /app
RUN mkdir -p /app/config /app/logs && chown -R kepler:kepler /app
COPY --from=builder --chown=kepler:kepler /app/build/libs/Kepler-*-all.jar kepler.jar
USER kepler
EXPOSE 30000
HEALTHCHECK --interval=30s --timeout=10s --start-period=90s --retries=3 \
  CMD nc -z localhost 30000 || exit 1
ENTRYPOINT ["java", "-XX:+UseZGC", "-Xms512m", "-Xmx2g", \
  "-XX:+HeapDumpOnOutOfMemoryError", "-XX:HeapDumpPath=/app/logs/", \
  "-Dfile.encoding=UTF-8", "-jar", "kepler.jar"]
```

**Canonical Dockerfile — PHP/Apache Webroot:**
```dockerfile
FROM php:8.2-apache
RUN apt-get update && apt-get install -y libpng-dev libjpeg-dev libwebp-dev \
    && docker-php-ext-configure gd --with-jpeg --with-webp \
    && docker-php-ext-install -j$(nproc) gd pdo pdo_mysql opcache \
    && apt-get clean && rm -rf /var/lib/apt/lists/*
RUN a2enmod rewrite headers expires
COPY docker/php/php-production.ini /usr/local/etc/php/conf.d/kepler.ini
COPY --chown=www-data:www-data webroot/ /var/www/html/
COPY --chown=www-data:www-data config/ /var/www/config/
EXPOSE 80
HEALTHCHECK --interval=20s --timeout=5s --start-period=15s --retries=3 \
  CMD curl -f http://localhost/ | grep -q 'habbo-client' || exit 1
```

**Canonical compose.yml:**
```yaml
version: '3.9'

services:
  kepler-mariadb:
    image: mariadb:10.11
    restart: unless-stopped
    environment:
      MYSQL_ROOT_PASSWORD: ${DB_ROOT_PASSWORD}
      MYSQL_DATABASE: ${DB_NAME:-kepler}
      MYSQL_USER: ${DB_USER:-kepler}
      MYSQL_PASSWORD: ${DB_PASSWORD}
    volumes:
      - kepler-mariadb:/var/lib/mysql
      - ./db/migrations:/docker-entrypoint-initdb.d:ro
    healthcheck:
      test: ["CMD", "healthcheck.sh", "--connect", "--innodb_initialized"]
      interval: 10s
      timeout: 5s
      retries: 5
      start_period: 30s
    networks:
      - kepler-internal

  kepler-server:
    build: .
    restart: unless-stopped
    depends_on:
      kepler-mariadb:
        condition: service_healthy
    environment:
      MYSQL_HOST: kepler-mariadb
      MYSQL_PORT: 3306
      MYSQL_USER: kepler
      MYSQL_PASSWORD: ${DB_PASSWORD}
      MYSQL_DATABASE: kepler
    ports:
      - "30000:30000"
      # 12309 (RCON) intentionally NOT published
    volumes:
      - ./config:/app/config:ro
      - kepler-logs:/app/logs
    networks:
      - kepler-internal
      - kepler-public

  kepler-web:
    build:
      context: .
      dockerfile: Dockerfile.web
    restart: unless-stopped
    ports:
      - "80:80"
    networks:
      - kepler-public

  kepler-minerva:
    build:
      context: ./Minerva
      dockerfile: Dockerfile
    restart: unless-stopped
    ports:
      - "3001:3001"
    networks:
      - kepler-internal
      - kepler-public

networks:
  kepler-internal:
    internal: true
  kepler-public: {}

volumes:
  kepler-mariadb:
  kepler-logs:
```

**Network segmentation rules:**
```
kepler-mariadb  → kepler-internal ONLY (never kepler-public, never published)
kepler-server   → BOTH networks (needs DB on internal + game port on public)
kepler-web      → kepler-public ONLY (serves HTML, no direct DB access)
kepler-minerva  → BOTH (Kepler API on internal + imaging port on public)
RCON 12309      → ABSENT from ALL ports: blocks (localhost loopback only)
MariaDB 3306    → ABSENT from ALL ports: blocks
```

---

## B3 — PHP / Apache / CMS

**THE WEBROOT IS FLAT. There is NO MVC framework.**

```
webroot/
├── index.php           # Shockwave client loader — THE critical file
├── login.php           # Login form + auth processing
├── register.php        # Registration form + account creation
├── me.php              # User dashboard (credits, motto, avatar)
├── community.php       # Online count, stats
├── news.php            # News articles from DB
├── help.php            # Static help content
├── signout.php         # Session destroy + redirect
├── submit.php          # Form action handler (login/register POST target)
├── update-motto.php    # AJAX-free motto update (full page reload)
├── avatarimage.php     # Proxy to Minerva imaging service
├── badges.php          # Proxy to Minerva badge rendering
├── ase/
│   └── index.php       # All-Seeing Eye admin panel
├── includes/
│   ├── config.php      # DB credentials, server settings (OUTSIDE DocumentRoot ideally)
│   ├── auth.php        # Session check, redirect if not logged in
│   └── db.php          # PDO connection factory
├── habbo.dcr           # Main Shockwave Director movie (static binary)
├── hh_entry_init.cct   # Initialization cast (static binary)
├── sec.cct             # Encryption module cast (static binary)
├── external_vars.txt   # Client configuration variables
├── external_texts.txt  # Localization strings
├── [furniture .cct]    # Dozens of furniture cast files
├── css/
│   └── style.css       # CSS2 ONLY — see era_lock
├── images/
│   └── [pixel art, UI elements, spacer.gif]
└── .htaccess           # Deny config access, disable directory listing
```

**Client loader (index.php) — the ONLY file the Shockwave plugin reads:**
```php
<?php
ob_start();
error_reporting(0);
ini_set('display_errors', '0');
require_once __DIR__ . '/includes/config.php';
require_once __DIR__ . '/includes/auth.php';

// Must be logged in with valid SSO ticket
if (!isset($_SESSION['user_id']) || !isset($_SESSION['sso_ticket'])) {
    header('Location: login.php');
    exit;
}

$e = fn(string $v): string => htmlspecialchars($v, ENT_QUOTES, 'UTF-8');
$host      = $e($config['server']['host']);
$port      = (int) $config['server']['port'];
$ticket    = $e($_SESSION['sso_ticket']);
$dcrPath   = $e($config['client']['dcr_path']);
$hotelName = $e($config['hotel']['name']);
ob_end_clean();
?><!DOCTYPE html>
<html>
<head><title><?= $hotelName ?></title></head>
<body bgcolor="#000000">
<table width="100%" height="100%"><tr><td align="center" valign="middle">
<object classid="clsid:166B1BCA-3F9C-11CF-8075-444553540000"
  codebase="http://download.macromedia.com/pub/shockwave/cabs/director/sw.cab#version=8,5,1,0"
  width="720" height="540" id="habbo-client">
  <param name="src" value="<?= $dcrPath ?>habbo.dcr">
  <param name="sw1" value="localhost">
  <param name="sw2" value="<?= $host ?>">
  <param name="sw3" value="<?= $port ?>">
  <param name="sw4" value="<?= $ticket ?>">
  <param name="sw5" value="<?= $dcrPath ?>">
  <param name="sw6" value="<?= $hotelName ?>">
  <param name="sw7" value="1">
  <param name="swRemote" value="swSaveEnabled='false' swVolume='false' swRestart='false'">
  <embed type="application/x-director" src="<?= $dcrPath ?>habbo.dcr"
    sw1="localhost" sw2="<?= $host ?>" sw3="<?= $port ?>" sw4="<?= $ticket ?>"
    sw5="<?= $dcrPath ?>" sw6="<?= $hotelName ?>" sw7="1"
    width="720" height="540" bgcolor="#000000"></embed>
</object>
</td></tr></table>
</body>
</html>
```

**PHP security baseline (php-production.ini):**
```ini
expose_php = Off
display_errors = Off
log_errors = On
error_log = /var/log/apache2/php_errors.log
session.cookie_httponly = 1
session.use_strict_mode = 1
session.cookie_samesite = Strict
memory_limit = 128M
max_execution_time = 15
opcache.enable = 1
opcache.validate_timestamps = 0
```

**PDO connection (includes/db.php):**
```php
<?php
declare(strict_types=1);

function getDb(): PDO {
    static $pdo = null;
    if ($pdo === null) {
        $config = require __DIR__ . '/config.php';
        $pdo = new PDO(
            sprintf('mysql:host=%s;port=%d;dbname=%s;charset=utf8mb4',
                $config['db']['host'], $config['db']['port'] ?? 3306, $config['db']['name']),
            $config['db']['user'],
            $config['db']['pass'],
            [PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
             PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
             PDO::ATTR_EMULATE_PREPARES => false]
        );
    }
    return $pdo;
}
```

**RCON from PHP (ASE admin commands):**
```php
<?php
function sendRcon(string $command, array $params = []): bool {
    $socket = @fsockopen('127.0.0.1', 12309, $errno, $errstr, 3);
    if (!$socket) return false;
    $payload = json_encode(array_merge(['command' => $command], $params));
    fwrite($socket, $payload);
    fclose($socket);
    return true;
}
// Usage: sendRcon('hotel_alert', ['message' => 'Server restart in 5 minutes']);
// Usage: sendRcon('disconnect_user', ['username' => 'baduser']);
```

**Apache MIME types for Shockwave (required in .htaccess or httpd.conf):**
```apache
AddType application/x-director .dcr .dxr .dir .cst .cxt .cco .w3d .fgd .swa
AddType application/x-shockwave-flash .swf
Options -Indexes
<FilesMatch "^\.">
  Require all denied
</FilesMatch>
<FilesMatch "config\.php$">
  Require all denied
</FilesMatch>
```

---

## B4 — C# / .NET / Minerva

**Minerva** renders Habbo avatar images and badges as PNG. Runs on .NET 8, Alpine linux-musl-x64, self-contained publish.

```csharp
// Minimal API — single endpoint per resource
var app = builder.Build();
app.MapGet("/avatar/render", AvatarEndpoints.RenderAsync)
   .Produces<byte[]>(200, "image/png")
   .ProducesProblem(400);
app.MapGet("/badge/render", BadgeEndpoints.RenderAsync)
   .Produces<byte[]>(200, "image/png")
   .ProducesProblem(400);
app.MapHealthChecks("/health");
```

**Publish command:** `dotnet publish -c Release -r linux-musl-x64 --self-contained`

**RetroLauncher** is a C# Shockwave projector wrapper that handles SSO ticket injection, eliminating the need for the deprecated NPAPI browser plugin. It connects to the web layer, authenticates, receives an SSO ticket, and launches the Shockwave projector with the ticket embedded.

**Non-negotiables:**
```
□ CSPRNG: RandomNumberGenerator (NEVER System.Random for tickets/tokens)
□ Timing-safe comparison: CryptographicOperations.FixedTimeEquals()
□ Input validation: FluentValidation on all endpoints
□ Path traversal: Verify all file paths with Path.GetFullPath() + StartsWith() check
□ Timeout: TimeSpan.FromMilliseconds(100) on all Regex instances
```

---

## B5 — Database / MariaDB

**Kepler uses 49 tables.** Schema auto-populates on first boot via `kepler-mariadb` init scripts.

**Key tables:**
```
users           — id, username, password (Argon2id hash), rank, credits, look, motto
users_bans      — user_id, ban_type, ban_expire, ban_reason
rooms           — id, owner_id, name, description, model, wallpaper, floor
rooms_rights    — room_id, user_id
items           — id, user_id, room_id, definition_id, x, y, z, wall_position
catalogue_pages — id, name_index, name, layout, image_headline
catalogue_items — id, sale_code, page_id, definition_id, price_coins, price_pixels
messenger_friends — user_id, friend_id
settings        — key, value (auto-populated by Kepler on first boot)
news            — id, title, body, author, created_at (Krassway extension)
```

**ALL queries use prepared statements. NEVER string concatenation in SQL.**
```php
// CORRECT
$stmt = getDb()->prepare('SELECT * FROM users WHERE username = :name');
$stmt->execute([':name' => $username]);

// FORBIDDEN — SQL injection vector
$result = getDb()->query("SELECT * FROM users WHERE username = '$username'");
```

**Password reset procedure (when agents corrupt hashes):**
```sql
-- Register a fresh user through the Kepler client, then promote:
UPDATE users SET rank = 7 WHERE username = 'newadmin';
-- NEVER insert raw Argon2id hashes manually — let Kepler generate them.
```

---

## B6 — Integration / Wiring

**Service startup order:** MariaDB → Kepler Server → Apache/PHP → Minerva

**Health check chain:**
```bash
# MariaDB ready?
docker exec kepler-mariadb healthcheck.sh --connect --innodb_initialized

# Kepler accepting connections?
nc -zv localhost 30000

# Web layer serving client loader?
curl -s http://localhost/ | grep -q 'habbo-client'

# Minerva rendering?
curl -s http://localhost:3001/health

# RCON isolated? (must return connection refused from external)
nc -zv <external_ip> 12309  # MUST FAIL
```

**Environment variables (.env):**
```env
DB_ROOT_PASSWORD=<strong-random>
DB_NAME=kepler
DB_USER=kepler
DB_PASSWORD=<strong-random>
KEPLER_HOST=your.domain.com
HOTEL_NAME=Krassway Hotel
```

**Common failure modes and fixes:**
```
Symptom: Black screen, no Shockwave client
  → Check: PHP errors in output (curl -s http://localhost/ | head -1)
  → Fix: Ensure ob_start() + display_errors=Off

Symptom: "Connection refused" in Shockwave
  → Check: Kepler game server running (nc -zv localhost 30000)
  → Fix: docker compose up -d kepler-server, check logs

Symptom: Login loops endlessly
  → Check: session_start() before any output, session save path writable
  → Fix: Verify require_once paths in flat webroot (use __DIR__ . '/')

Symptom: DCR files download instead of loading in plugin
  → Check: Apache MIME type for .dcr
  → Fix: AddType application/x-director .dcr .cct .cxt in .htaccess

Symptom: Admin password doesn't work
  → Check: Argon2id hash integrity in users table
  → Fix: Register new user via client, promote with SQL UPDATE
```
