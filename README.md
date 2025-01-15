# Code 실행시 사전 준비 사항

## 1. PostgreSQL  구성

-   bashrc 또는 zshrc 파일을 개인 설정에 맞게 재설정함
    ```sh
    export POSTGRESQL_USER=sky              # Postgres SQL 접속 ID
    export POSTGRESQL_PASSWORD=123456       # Postgres SQL 접속 Password
    export POSTGRESQL_DATABASE=stock        # Postgres SQL 접속 DB name
    ```
    -   편집 후 source 명령으로 terminal 환경에 적용(실행시 환경 변수 반영)
    -   source 적용후 env 파일을 .env 파일로 변환(설정 구성시 환경 변수 반영)
        -   docker compose는 docker-compose.yml 파일과 같은 위치에 .env 존재하면 명령 실행시 값을 읽어서 설정
   
-   아래 docker compose 명령을 통해 DB를 구성해줌
    ```shell
    sudo docker-compose up -d
    # 일부 docker-compose 버전이 높은 경우
    sudo docker compose up -d
    ```
