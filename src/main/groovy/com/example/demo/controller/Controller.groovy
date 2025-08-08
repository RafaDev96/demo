package com.example.demo.controller

import groovy.transform.CompileStatic
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import com.example.demo.service.ItemService // Importe o novo serviço
import org.springframework.beans.factory.annotation.Autowired

@RestController
@RequestMapping("/api/items")
@CompileStatic
class Controller {

    // Injete a dependência do nosso serviço
    @Autowired
    private ItemService itemService

    // Endpoint 1: GET para listar todos os itens
    @GetMapping
    List<String> getAllItems() {
        return itemService.getAllItems()
    }

    // Endpoint 2: GET para buscar um item específico por ID
    @GetMapping("/{id}")
    ResponseEntity<String> getItem(@PathVariable Integer id) {
        String item = itemService.getItem(id)
        if (item) {
            return ResponseEntity.ok(item)
        } else {
            return new ResponseEntity<>("Item não encontrado.", HttpStatus.NOT_FOUND)
        }
    }

    // Endpoint 3: POST para criar um novo item
    @PostMapping
    ResponseEntity<String> createItem(@RequestBody String newItem) {
        // 1. O Controller chama o serviço e salva o resultado.
        Integer newId = itemService.createItem(newItem)

        // 2. O Controller verifica o resultado.
        // Se 'newId' for diferente de null (ou seja, a operação deu certo)...
        if (newId) {
            // 3. O Controller monta a resposta de SUCESSO.
            // status 201 (Created) e uma mensagem amigável.
            return new ResponseEntity<>("Item criado com sucesso com ID: ${newId}" as String, HttpStatus.CREATED)
        } else {
            // 4. Se 'newId' for null (ou seja, a operação no serviço falhou)...
            // O Controller precisa montar uma resposta de ERRO.

            // Vamos checar qual foi o erro para dar uma mensagem mais específica.
            // Podemos fazer isso checando a contagem de itens aqui, já que o 'if' do serviço já falhou.
            if (itemService.getAllItems().size() >= 5) {
                // Se o limite foi atingido, retornamos um erro 403 (Forbidden)
                // e uma mensagem clara para o usuário.
                return new ResponseEntity<>("Limite de 5 itens atingido. Não é possível adicionar mais itens.", HttpStatus.FORBIDDEN)
            } else {
                // Se o limite não foi atingido, o erro deve ser por causa da validação do nome.
                // Retornamos um erro 400 (Bad Request).
                return new ResponseEntity<>("O nome do item é inválido.", HttpStatus.BAD_REQUEST)
            }
        }
    }

    // Endpoint 4: PUT para atualizar um item existente
    @PutMapping("/{id}")
    ResponseEntity<String> updateItem(@PathVariable Integer id, @RequestBody String updatedItem) {
        if (itemService.updateItem(id, updatedItem)) {
            return ResponseEntity.ok("Item ${id} atualizado com sucesso.".toString())
        } else {
            return new ResponseEntity<>("Item não encontrado.", HttpStatus.NOT_FOUND)
        }
    }

    // Endpoint adicional para deletar um item
    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteItem(@PathVariable Integer id) {
        if (itemService.deleteItem(id)) {
            return ResponseEntity.ok("Item ${id} deletado com sucesso.".toString())
        } else {
            return new ResponseEntity<>("Item não encontrado.", HttpStatus.NOT_FOUND)
        }
    }
}