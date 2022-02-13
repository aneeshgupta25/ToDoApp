package com.example.todonotesapp.clickListeners

import com.example.todonotesapp.db.Notes

interface SetClickListener {
    fun onClick(note : Notes);
    fun onUpdate(note: Notes)
}