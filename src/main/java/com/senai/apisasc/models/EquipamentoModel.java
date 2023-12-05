package com.senai.apisasc.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tb_equipamento")
public class EquipamentoModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    private Date data_compra;

    @ManyToOne
    @JoinColumn(name = "id_setor", referencedColumnName = "id")
    private SetorModel setor;

    @ManyToOne
    @JoinColumn(name = "id_modelo", referencedColumnName = "id")
    private ModeloModel modelo;
}