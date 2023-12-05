package com.senai.apisasc.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tb_consumo_equipamento")
public class ConsumoEquipamentoModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    private BigDecimal consumo;

    private Date data_consumo;

    @ManyToOne
    @JoinColumn(name = "id_equipamento", referencedColumnName = "id")
    private EquipamentoModel equipamento;

}