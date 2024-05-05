# API Pessoa

Esta API é usada para gerenciar pessoas e seus endereços.

## Endpoints

### Pessoa

- `POST /v1/person`: Cria uma nova pessoa.
  - Exemplo de requisição:
    ```json
    {
      "name": "João",
      "birthDate": "1990-01-01"
    }
    ```
- `GET /v1/person`: Lista todas as pessoas.
- `GET /v1/person/{personId}`: Obtém os detalhes de uma pessoa específica pelo ID.
  - Exemplo de resposta:
    ```json
    {
      "id": 1,
      "name": "João",
      "birthDate": "1990-01-01",
      "mainAddress": {
        "id": 1,
        "street": "Rua das Flores",
        "city": "São Paulo",
        "state": "SP",
        "zipCode": "01234-567"
      },
      "addresses": [
        {
          "id": 1,
          "street": "Rua das Flores",
          "city": "São Paulo",
          "state": "SP",
          "zipCode": "01234-567"
        },
        {
          "id": 2,
          "street": "Rua das Pedras",
          "city": "São Paulo",
          "state": "SP",
          "zipCode": "01234-567"
        }
      ]
    }
    ```
- `PUT /v1/person/{personId}`: Atualiza os detalhes de uma pessoa específica pelo ID.
- `DELETE /v1/person/{personId}`: Exclui uma pessoa específica pelo ID.
- `PUT /v1/person/{userId}/address/{addressId}`: Define o endereço principal de uma pessoa específica.

### Endereço

- `POST /v1/address/{personId}/address`: Cria um novo endereço para uma pessoa específica.
  - Exemplo de requisição:
    ```json
    {
      "street": "Rua das Flores",
      "city": "São Paulo",
      "state": "SP",
      "zipCode": "01234-567"
    }
    ```
- `GET /v1/address`: Lista todos os endereços.
- `GET /v1/address/{addressId}`: Obtém os detalhes de um endereço específico pelo ID.
- `PUT /v1/address/{addressId}`: Atualiza os detalhes de um endereço específico pelo ID.
- `DELETE /v1/address/{addressId}`: Exclui um endereço específico pelo ID.
- `PUT /v1/address/{personId}/setMain/{addressId}`: Define o endereço principal de uma pessoa específica.
