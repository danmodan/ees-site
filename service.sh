#!/bin/bash

BASEDIR="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"

run_maven() {

    docker run --rm \
               -w /build \
               -v $BASEDIR:/build \
               -v ~/.m2:/root/.m2 \
               maven:3-adoptopenjdk-11-openj9 $@
}

run_node() {

    docker run -it \
               --rm \
               -v $BASEDIR:/usr/src/app \
               -w /usr/src/app \
               node $@
}

case $1 in
    npm-install)
        run_node npm --prefix theme/ install
        ;;
    compile-sass)
        run_node npx sass --watch theme/scss/main.scss:public/css/main.css --style compressed
        ;;
    up-function)
        docker-compose up -d function.ees.dev
        ;;
    log-function)
        docker logs -f --tail 1000 function.ees.dev
        ;;
    down)
        docker-compose down
        ;;
    build)
        run_maven mvn clean package -f function/pom.xml -DskipTests --batch-mode
        ;;
    deploy-function)
        gcloud functions deploy ees-contact-function --entry-point br.com.ees.function.ContactFormFunction \
                                                     --runtime java11 \
                                                     --trigger-http \
                                                     --memory 512MB \
                                                     --source function \
                                                     --allow-unauthenticated
        ;;
    *)
        echo -e "Invalid option"
        ;;
esac
