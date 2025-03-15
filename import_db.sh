#!/bin/bash
DB_USER="root"
DB_NAME="moviedb"
BACKUP_FILE="database_backup.sql"

echo "Pulling latest database backup from GitHub..."
git pull origin main

echo "Importing database..."
mysql -u $DB_USER -p $DB_NAME < $BACKUP_FILE
echo "Database imported successfully"
