package com.example.demo.service

import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap
import java.util.ArrayList

@Service
class ItemService {

    private final ConcurrentHashMap<Integer, String> items = new ConcurrentHashMap<>()
    private Integer nextId = 1

    ItemService() {
        items.put(nextId++, "Maça")
        items.put(nextId++, "Banana")
    }

    List<String> getAllItems() {
        return new ArrayList<>(items.values())
    }

    String getItem(Integer id) {
        return items.get(id)
    }

    Integer createItem(String newItem) {
        // 1. Aqui, você insere a LÓGICA DE NEGÓCIO.
        // Primeiro, vamos checar se o número de itens é maior ou igual a 5.
        if (items.size() >= 5) {
            // 2. Se a regra de negócio for violada, o método deve parar a execução e
            // indicar que algo deu errado.
            // Neste caso, vamos retornar 'null' para sinalizar um erro.
            // O método "createItem" não deve saber o que acontece depois disso,
            // apenas que não foi possível criar o item.
            return null
        }

        // 3. Se a regra de negócio for atendida (ou seja, o 'if' acima não foi ativado),
        // a execução do código continua normalmente.
        // As outras validações e a lógica de criação do item vêm aqui.
        if (!isNameValid(newItem)) {
            return null
        }

        Integer newId = nextId++
        items.put(newId, newItem)
        return newId
    }

    boolean updateItem(Integer id, String updatedItem) {
        if (!isNameValid(updatedItem)) {
            return false
        }
        if (items.containsKey(id)) {
            items.put(id, updatedItem)
            return true
        }
        return false
    }

    boolean deleteItem(Integer id) {
        if (items.containsKey(id)) {
            items.remove(id)
            return true
        }
        return false
    }

    private boolean isNameValid(String name) {
        // A lógica da sua nova regra está aqui.
        boolean valid = name != null && name.trim().length() > 2

        if (valid) {
            if (name.equalsIgnoreCase("Caju")) {
                valid = false
            }
        }
        return valid
    }
}