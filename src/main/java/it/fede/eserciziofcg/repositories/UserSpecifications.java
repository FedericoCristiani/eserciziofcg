package it.fede.eserciziofcg.repositories;

import it.fede.eserciziofcg.models.entities.UserEntity;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {
    public static Specification<UserEntity> withFirstName(String firstName) {
        return (book, cq, cb) -> cb.equal(book.get("firstname"), firstName);
    }

    public static Specification<UserEntity> withLastName(String lastName) {
        return (book, cq, cb) -> cb.equal(book.get("lastname"), lastName);
    }

    public static Specification<UserEntity> build(String firstname, String lastname) {
        Specification<UserEntity> userEntitySpecification = Specification.where(null);
        if( !firstname.isBlank()){
            userEntitySpecification = userEntitySpecification.and(UserSpecifications.withFirstName(firstname));
        }
        if( !lastname.isBlank()){
            userEntitySpecification= userEntitySpecification.and(UserSpecifications.withLastName(lastname));
        }

        return userEntitySpecification;

    }
}
