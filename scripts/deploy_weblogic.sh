#!/bin/bash

# === CONFIGURATION ===
ARTIFACTORY_URL="http://<your-artifactory-host>/artifactory"
REPO="libs-release-local"
APP_PATH="my-app"
VERSION="1.0.1"  # Or read from version.txt or passed as an argument
FILENAME="EbookWebsite-1.0-SNAPSHOT.war"
DOWNLOAD_PATH="/tmp/${FILENAME}"
DEPLOY_DIR="/Oracle/Middleware/Oracle_Home/user_projects/domains/base_domain/autodeploy"

# === AUTHENTICATION ===
ARTIFACTORY_SERVER = 'jFrog_Artifactory'
ARTIFACTORY_CRED = credentials('jFrog_Credentials')

# === DOWNLOAD ARTIFACT ===
echo "Downloading ${FILENAME} from Artifactory..."

curl -u ${USERNAME}:${API_KEY} -o ${DOWNLOAD_PATH} "${ARTIFACTORY_URL}/${REPO}/${APP_PATH}/${VERSION}/${FILENAME}"

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