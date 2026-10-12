# TripMate database setup

TripMate uses a Spring Boot backend and a MySQL-compatible TiDB Cloud database.

## Tables

- `users` — user accounts (store password hashes, never plaintext passwords).
- `destinations` — supported travel destinations.
- `places` — places linked to a destination.
- `trips` — saved trip plans linked to a user and destination.
- `trip_places` — places selected for a trip and the planned visit day.

## Create a fresh database schema

1. Create a new, empty MySQL-compatible database/cluster.
2. Connect using a SQL client with a database account that has permission to create tables.
3. Run `database/schema.sql`.
4. Add destination and place seed data separately if you want the app to show example destinations. This schema intentionally contains no real user records or sample passwords.

**Warning:** `schema.sql` is for a new/empty database. Do not run it as a migration against your existing production database without first making a backup and reviewing the differences. It uses `CREATE TABLE IF NOT EXISTS`; that does not alter existing tables to match the script.

## Configure the backend

Set connection details as environment variables in your hosting provider (for example, Render). Do not commit actual values to GitHub.

Required variables:

- `DB_URL` — JDBC URL, for example `jdbc:mysql://YOUR_DB_HOST:4000/tripmate?sslMode=VERIFY_IDENTITY&serverTimezone=UTC`
- `DB_USERNAME` — database username
- `DB_PASSWORD` — database password
- `JWT_SECRET` — a strong random secret used to sign authentication tokens

Optional variables depend on the backend configuration, such as `JWT_EXPIRATION_MS`, `SPRING_JPA_HIBERNATE_DDL_AUTO`, `AI_API_KEY`, and `AI_PROVIDER`.

The exact variable names must match the placeholders in the backend's `application.properties` and `application-prod.properties`.

## Local configuration

Use environment variables or a local, untracked `.env` file if your local tools support it. Make sure `.env` and `.env.*` are ignored by Git. Never place real database credentials, JWT secrets, API keys, or production tokens in Java properties files committed to the repository.

## Current schema compatibility note

The supplied live schema contains both `trips.destination` (nullable legacy field) and `trips.destination_id` (foreign key to `destinations.id`). The schema file preserves both columns for compatibility. New trip inserts use `destination_id`; the legacy `destination` field remains nullable.

The supplied schema also has nullable `trips.user_id` even though the current Java entity may declare the relationship non-nullable. If the Java entity and database constraints differ, verify the actual application mappings before changing production.

## Security checklist

- Never commit credentials or production data.
- Never export or commit rows from `users`.
- Use a restricted database user for application access where possible.
- Back up the live database before any schema migration.
- Rotate any credential that may have been exposed.
