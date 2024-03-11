package com.example.examenpracticadi.Utility;

import com.example.examenpracticadi.Domain.Usuario;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.dialect.unique.AlterTableUniqueDelegate;

public class Session {
    @Getter
    @Setter
    private static Usuario currentUser;
}
