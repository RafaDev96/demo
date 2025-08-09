package com.example.demo.controller

import com.example.demo.controller.payloads.ItemPayload
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
    List<ItemPayload> getAllItems() {
        return itemService.getAllItems()
    }

    // Endpoint 2: GET para buscar um item específico por ID
    @GetMapping("/{id}")
    ResponseEntity<?> getItem(@PathVariable Integer id) {
        ItemPayload item = itemService.getItem(id)
        if (item) {
            return ResponseEntity.ok(item)
        } else {
            return new ResponseEntity<>("Item não encontrado.", HttpStatus.NOT_FOUND)
        }
    }

    // Endpoint 3: POST para criar um novo item
    @PostMapping
    ResponseEntity<?> createItem(@RequestBody ItemPayload newItem) {
        // 1. O Controller chama o serviço e salva o resultado.
        Integer newId = itemService.createItem(newItem)

        // 2. O Controller verifica o resultado.
        // Se 'newId' for diferente de null (ou seja, a operação deu certo)...
        if (newId) {
            // 3. O Controller monta a resposta de SUCESSO.
            // status 201 (Created) e uma mensagem amigável.
            return new ResponseEntity<>("Item criado com sucesso com ID: ${newId}" as String, HttpStatus.CREATED)
        }


    }

    // Endpoint 4: PUT para atualizar um item existente
    @PutMapping("/{id}")
    ResponseEntity<String> updateItem(@PathVariable Integer id, @RequestBody ItemPayload updatedItem) {
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