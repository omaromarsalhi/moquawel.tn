package com.stage.authentification.token;

import com.stage.authentification.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface TokenBlackListRepository extends JpaRepository<TokenBlackList, Integer> {


    @Modifying
    @Transactional
    @Query("""
            UPDATE TokenBlackList t set t.expiresAt=CURRENT_TIMESTAMP WHERE t.user.idUser=:idUser 
            and t.jti != :jti
            """)
    void expireTokenByUser(Integer idUser,String jti);



    @Modifying
    @Transactional
    @Query("""
            UPDATE TokenBlackList t set t.expiresAt=CURRENT_TIMESTAMP WHERE t.user.idUser=:idUser 
            """)
    void expireAllTokensByUser(Integer idUser);

    @Query("SELECT COUNT(t) > 0 FROM TokenBlackList t WHERE t.jti= :jti AND t.expiresAt IS NULL ")
    boolean existsNonExpiredTokens(String jti);
}
