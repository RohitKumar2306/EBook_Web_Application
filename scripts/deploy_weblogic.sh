#!/bin/bash

# === CONFIGURATION ===
ARTIFACTORY_URL="http://192.168.0.146:8082"
REPO="libs-release-local"
APP_PATH="my-app"
VERSION="1.0.2"  # Or read from version.txt or passed as an argument
FILENAME="EbookWebsite-1.0-SNAPSHOT.war"
DOWNLOAD_PATH="/tmp/${FILENAME}"
DEPLOY_DIR="/home/rohitkumar/Oracle/Middleware/Oracle_Home/user_projects/domains/base_domain/autodeploy"

# === DOWNLOAD ARTIFACT ===
echo "Downloading ${FILENAME} from Artifactory..."

curl -H "Authorization: Bearer $ACCESS_TOKEN" \
     -o "${DOWNLOAD_PATH}" \
     "${ARTIFACTORY_URL}/${REPO}/${APP_NAME}/${VERSION}/${FILENAME}"

if [ $? -ne 0 ]; then
    echo "❌ Download failed. Check URL or credentials."
    exit 1
fi

# === DEPLOY TO WEBLOGIC ===
echo "Deploying to WebLogic at ${DEPLOY_DIR}..."
cp ${DOWNLOAD_PATH} ${DEPLOY_DIR}/

if [ $? -eq 0 ]; then
    echo "✅ Deployment successful!"
else
    echo "❌ Deployment failed."
    exit 1
fi