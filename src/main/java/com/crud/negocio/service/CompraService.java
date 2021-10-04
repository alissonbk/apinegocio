package com.crud.negocio.service;

import com.crud.negocio.dto.*;
import com.crud.negocio.exception.RegraNegocioException;
import com.crud.negocio.model.Cliente;
import com.crud.negocio.model.Compra;
import com.crud.negocio.model.Produto;
import com.crud.negocio.model.Vendedor;
import com.crud.negocio.repository.ClienteRepository;
import com.crud.negocio.repository.CompraRepository;
import com.crud.negocio.repository.ProdutoRepository;
import com.crud.negocio.repository.VendedorRepository;
import lombok.RequiredArgsConstructor;;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static com.crud.negocio.repository.CompraRepository.*;


@Service
@RequiredArgsConstructor
public class CompraService {

    private final CompraRepository compraRepository;
    private final ClienteRepository clienteRepository;
    private final VendedorRepository vendedorRepository;
    private final ProdutoRepository produtoRepository;


    public Compra cadastrar(CompraDTO dto){
        Long idCliente = dto.getCliente();
        Long idVendedor = dto.getVendedor();
        Long idProduto = dto.getProduto();

        Cliente cliente = clienteRepository
                .findById(idCliente)
                .orElseThrow( () -> new RegraNegocioException("Cliente não encontrado!"));

        Vendedor vendedor = vendedorRepository
                .findById(idVendedor)
                .orElseThrow( () -> new RegraNegocioException("Vendedor não encontrado!"));

        Produto produto = produtoRepository
                .findById(idProduto)
                .orElseThrow( () -> new RegraNegocioException("Produto não encontrado!"));


        Compra compra = new Compra();
        compra.setDataCompra(Instant.now());
        compra.setCliente(cliente);
        compra.setVendedor(vendedor);
        compra.setProduto(produto);

        compraRepository.save(compra);
        return compra;
    }

    public void excluirCompra(Long id){
       this.compraRepository.findById(id)
                .map(c -> {
                    compraRepository.delete(c);
                    return c;
                }).orElseThrow( () -> new RegraNegocioException("Compra não encontrada!"));
    }

    public InfoCompraDTO obterCompra(Long id) {
        return compraRepository.findById(id)
                .map(this::converter)
                .orElseThrow(() -> new RegraNegocioException("Compra não encontrada!"));
    }




    public Page<InfoCompraDTO> obterTodasCompras(Pageable pageable, Long c, Long v, Long p){
        return compraRepository.findAll((filtroVendedorClienteCompra(c, v, p)), pageable)
                .map(this::converter);


    }




    private InfoCompraDTO converter(Compra compra){
        return InfoCompraDTO
                .builder()
                .codigo(compra.getId())
                .dataCompra(compra.getDataCompra().toString())
                .cliente(converterCliente(compra.getCliente()))
                .vendedor(converterVendedor(compra.getVendedor()))
                .produto(converterProduto(compra.getProduto()))
                .build();
    }



    private InfoClienteDTO converterCliente(Cliente cliente){
        return InfoClienteDTO.builder()
                .codigo(cliente.getId())
                .nome(cliente.getNome())
                .idade(cliente.getIdade())
                .cpf(cliente.getCpf())
                .build();
    }

    private InfoVendedorDTO converterVendedor(Vendedor vendedor){
        return InfoVendedorDTO.builder()
                .codigo(vendedor.getId())
                .nome(vendedor.getNome())
                .idade(vendedor.getIdade())
                .cpf(vendedor.getCpf())
                .build();
    }

    private InfoProdutoDTO converterProduto(Produto p){
        return InfoProdutoDTO.builder()
                .codigo(p.getId())
                .valor(p.getValor())
                .descricao(p.getDescricao())
                .build();
    }



}
