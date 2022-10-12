package br.com.kronos.backend.adaptadores.seguranca;

import java.io.Serializable;


public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 8571261118900116242L;

    private Integer id;
    private String createdAt;
    private String updatedAt;

    public BaseEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
