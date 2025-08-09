package com.example.demo.service

import com.example.demo.controller.payloads.ItemPayload
import groovy.transform.CompileStatic
import org.apache.coyote.BadRequestException
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@CompileStatic
@Service
class ItemService {

    private final ConcurrentHashMap<Integer, ItemPayload> items = new ConcurrentHashMap<>()
    private Integer nextId = 1

    ItemService() {
        items.put(nextId++, new ItemPayload("Maça"))
        items.put(nextId++, new ItemPayload("Banana"))
    }

    List<ItemPayload> getAllItems() {
        return new ArrayList<>(items.values())
    }

    ItemPayload getItem(Integer id) {
        return items.get(id)
    }

    Integer createItem(ItemPayload newItem) {
        if (!newItem.isNameValid()) {
            throw new BadRequestException("Nome Inválido.")
        }

        Integer newId = nextId++
        items.put(newId, newItem)
        return newId
    }

    boolean updateItem(Integer id, ItemPayload updatedItem) {
        if (!updatedItem.isNameValid()) {
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
}