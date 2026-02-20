# Krassway Stack Constants
# Authoritative reference for ports, networks, security parameters, and era constraints.

PORTS:
  GamePort:      30000  # external TCP
  RconPort:      12309  # 127.0.0.1 ONLY — never publish
  WebPort:       80     # HTTP / 443 HTTPS
  MariaDbPort:   3306   # kepler-internal only
  MinervaPort:   3001

DOCKER:
  InternalNetwork: kepler-internal (internal: true)
  PublicNetwork:   kepler-public
  DbVolume:        kepler-mariadb
  MysqlHost:       kepler-mariadb (never localhost)

CRYPTOGRAPHY:
  Algorithm:   Argon2id
  Memory:      ≥ 65536
  Iterations:  ≥ 3
  Parallelism: ≥ 4
  CSPRNG_Java: SecureRandom
  CSPRNG_PHP:  random_bytes(32)
  CSPRNG_CS:   RandomNumberGenerator

PHP:
  Version:        8.2+
  DisplayErrors:  Off
  Session:        httponly=1, samesite=Strict, use_strict_mode=1

ERA_LOCK:
  Target:         Habbo Hotel v14 (2007 Shockwave)
  Browser CSS:    CSS2.1 ONLY (no flex, grid, border-radius, box-shadow)
  Browser HTML:   XHTML 1.0 Transitional (no HTML5 semantic elements)
  Browser JS:     ES3 inline handlers (no jQuery, no AJAX, no ES6)
  Layout:         Table-based, 760-780px fixed width, Verdana 11px
  Webroot:        FLAT — no MVC directories (views/, controllers/, models/)
  Watchdog:       HABBOX agent monitors all browser-facing output
