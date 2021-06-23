# 발자국 Backend
### 실행 방법
- 일반적인 방법으로 실행하면 local (개발환경) 으로 실행됩니다.
    ```
    // 개발환경으로 실행됨
    java -jar rush-0.0.1-SNAPSHOT.jar
    ```
- 실서버 환경으로 실행하려면 다음과 같이 옵션을 줘야합니다.
    ```
    java -jar rush-0.0.1-SNAPSHOT.jar REAL
    // 또는
    java -jar rush-0.0.1-SNAPSHOT.jar real
    ```
    실서버 환경으로 실행시, 모든 백엔드 yml 파일들은 `/app/config` 경로에 위치해야합니다.