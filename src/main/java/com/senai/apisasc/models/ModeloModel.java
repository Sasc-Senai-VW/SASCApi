package com.senai.apisasc.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tb_modelo")
public class ModeloModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    private String modelo;
    private BigDecimal consumo_nominal;

    @ManyToOne
    @JoinColumn(name = "id_fabricante", referencedColumnName = "id")
    private FabricanteModel fabricante;

    @ManyToOne
    @JoinColumn(name = "id_tipoequipamento", referencedColumnName = "id")
    private TipoEquipamentoModel tipoequipamento;

}