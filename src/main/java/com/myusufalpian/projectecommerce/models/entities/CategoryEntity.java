package com.myusufalpian.projectecommerce.models.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "tb_kategori")
public class CategoryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Column(name = "nama_kategori", length = 35)
    private String nama;
}
