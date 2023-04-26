package com.patryk.app.rest.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.patryk.app.rest.Model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

}
