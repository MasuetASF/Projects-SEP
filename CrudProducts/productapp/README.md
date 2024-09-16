# Guia de endpoints da aplicação

Este guia tem como intenção orientar e facilitar o teste e vizualização das reqiuisições desta simples API de produtos. A aplicação foi baseada e elaborada seguindo o princípio de boas práticos de uma API Restful de hateoas.

### Endpoints Produto

Registrar um Produto / Método POST ->
http://localhost:8080/products

json:
~~~json
    {	
        "name": "Controle PS5",
	    "valueProduct": 300.00
    }
~~~

---


Listar produtos / Método GET ->
http://localhost:8080/products

---

Listar um produto pelo ID(UUID) / Método GET -> http://localhost:8080/products/{UUID-do-produto}

OBS: id é gerado automáticamente.
---

Atualizar um produto / Método PUT -> http://localhost:8080/products/{UUID-do-produto}

json:
~~~json
    {
        "name": "Controle PS4",
	    "valueProduct": 354.56
    }
~~~~

---

Deletar produto através do ID / Método  DELETE -> http://localhost:8080/products/{UUID-do-produto}

---

