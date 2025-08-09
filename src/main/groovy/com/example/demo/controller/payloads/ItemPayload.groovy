package com.example.demo.controller.payloads


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.CompileStatic

@CompileStatic
class ItemPayload {
    private String item

    ItemPayload(String item) {
        this.item = item
    }

    String getItem() {
        return item
    }

    void setItem(String item) {
        this.item = item
    }
    @JsonIgnoreProperties
    boolean isNameValid() {
        // A lógica da sua nova regra está aqui.
        boolean valid = this.item != null && this.item.trim().length() > 2

        if (valid) {
            if (this.item.equalsIgnoreCase("Caju")) {
                valid = false
            }
        }
        return valid
    }

}
