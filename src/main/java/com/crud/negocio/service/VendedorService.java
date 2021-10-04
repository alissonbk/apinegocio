package com.crud.negocio.service;

import com.crud.negocio.exception.RegraNegocioException;
import com.crud.negocio.model.Vendedor;
import com.crud.negocio.repository.VendedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendedorService {
    private final VendedorRepository repository;

    public VendedorService(VendedorRepository repository) {
        this.repository = repository;
    }


    public Vendedor salvar(Vendedor v){
        return this.repository.save(v);
    }

    public List<Vendedor> listarTodos(String nome){
        if(nome != null){
            return this.repository.findByNomeContains(nome);
        }else{
            return this.repository.findAll();
        }

    }

    public Vendedor listarId(Long id){
        return this.repository.findById(id)
                .orElseThrow( () -> new RegraNegocioException("Vendedor não encontrado!"));
    }

    public void excluir(Long id){
        this.repository.findById(id)
                .map(v -> {
                    this.repository.delete(v);
                    return v;
                }).orElseThrow( () -> new RegraNegocioException("Vendedor não encontrado!"));
    }

    public void update(Long id, Vendedor vendedor){
        this.repository.findById(id)
                .map( v -> {
                    vendedor.setId(v.getId());
                    this.repository.save(vendedor);
                    return v;
                }).orElseThrow( () -> new RegraNegocioException("Vendedor não encontrado!"));
    }
}
