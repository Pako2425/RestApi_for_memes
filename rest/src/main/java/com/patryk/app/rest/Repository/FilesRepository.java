package com.patryk.app.rest.Repository;

import com.patryk.app.rest.Model.FileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilesRepository extends JpaRepository<FileData, Long> {}
