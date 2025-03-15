#!/bin/bash
DB_USER="root"
DB_NAME="moviedb"
BACKUP_FILE="database_backup.sql"

echo "Exporting database..."
mysqldump -u $DB_USER -p $DB_NAME > $BACKUP_FILE
echo "Database exported to $BACKUP_FILE"

git add $BACKUP_FILE
git commit -m "Updated database backup"
git push origin main
echo "Database backup pushed to GitHub"
