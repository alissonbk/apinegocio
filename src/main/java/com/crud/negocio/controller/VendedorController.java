package com.crud.negocio.controller;

import com.crud.negocio.model.Cliente;
import com.crud.negocio.model.Vendedor;
import com.crud.negocio.service.VendedorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendedores")
public class VendedorController {

    private final VendedorService service;
    public VendedorController(VendedorService service) { this.service = service; }


    @GetMapping()
    public List<Vendedor> listarTodos(@RequestParam(value="nome", required = false) String nome){
        return service.listarTodos(nome);
    }

    @GetMapping("{id}")
    public Vendedor listarId(@PathVariable Long id){
        return service.listarId(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Vendedor cadastrar(@RequestBody Vendedor vendedor){
        return service.salvar(vendedor);
    }

    @DeleteMapping("{id}")
    public void deletar(@PathVariable Long id){
        service.excluir(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @RequestBody Vendedor vendedor){
        service.update(id, vendedor);
    }



}
