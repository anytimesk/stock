# Code 실행시 사전 준비 사항

## 1. PostgreSQL, Elastic Search, Kibana 구성

-   bashrc 또는 zshrc 파일을 개인 설정에 맞게 재설정함

    ```sh
    export POSTGRESQL_USER=sky              # Postgres SQL 접속 ID
    export POSTGRESQL_PASSWORD=123456       # Postgres SQL 접속 Password
    export POSTGRESQL_DATABASE=stock        # Postgres SQL 접속 DB name
    ```

    -   편집 후 source 명령으로 terminal 환경에 적용(실행시 환경 변수 반영)

    ```
    source .bashrc
    # 또는
    source .zshrc
    ```

-   아래 docker compose 명령을 통해 DB, Elastic Search, Kibana를 구성해 줌
    ```shell
    sudo docker-compose up -d
    # 일부 docker-compose 버전이 높은 경우
    sudo docker compose up -d
    ```

## 2. Oauth2 사용 설정

-   사전에 Kakao, Google등 OAuth2 제공자에서 OAuth2 애플리케이션을 생성해야 함
-   본 프로젝트는 Google Cloud의 API 서비스를 통해 프로젝트를 생성([Google OAuth2 문서](https://developers.google.com/identity/protocols/oauth2?hl=ko))
-   /src/main/resources 폴더 아래에 application-oauth2.yml 작성해 추가(민감한 정보가 있기 때문에 yml을 분리하고 해당 부분은 주석처리)
-   applicaiton-oauth2.yml을 포함시키는 방법

    -   applcation.yml에 아래 코드를 포함시킨다.

    ```yml
    spring:
        profiles:
            include: oauth2
    ```

-   application-oauth2.yml 샘플(For Google)
    ```yml
    spring:
        security:
            oauth2:
                client:
                    registration:
                        google:
                            client-id: #google client id
                            client-secret: #google client password
                            scope: profile,email
                            redirect-uri: "{baseUrl}/login/oauth2/code/{registrationId}"
                            authorization-grant-type: authorization_code
                    provider:
                        google:
                            authorization-uri: https://accounts.google.com/o/oauth2/auth
                            token-uri: https://oauth2.googleapis.com/token
                            user-info-uri: https://www.googleapis.com/oauth2/v3/userinfo
    ```
