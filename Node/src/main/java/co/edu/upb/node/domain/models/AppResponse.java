package co.edu.upb.node.domain.models;

import java.io.Serializable;

public record AppResponse<DataType>(boolean success, String message, DataType data) implements Serializable {

    private static final long serialVersionUID = 1L;
}
