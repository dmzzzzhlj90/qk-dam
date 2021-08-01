package com.qk.dm.auth.jose;

import com.qk.dm.auth.config.InnerRegisteredClient;
import java.sql.Types;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Service;

@Service
public class ClientKeyService {
  private static final String TABLE_NAME = "oauth2_registered_client_key";
  private static final String COLUMN_NAMES =
      "" + "client_id, " + "client_public_key, " + "client_private_key ";
  private static final String SAVE_CLIENT_KEY_SQL =
      "INSERT INTO " + TABLE_NAME + " (" + COLUMN_NAMES + ") VALUES (?, ?, ?)";
  private static final String SELECT_CLIENT_KEY_SQL =
      "select " + COLUMN_NAMES + " from " + TABLE_NAME + " where client_id = ?";
  private final JdbcOperations jdbcOperations;

  public ClientKeyService(JdbcOperations jdbcOperations) {
    this.jdbcOperations = jdbcOperations;
  }

  public InnerRegisteredClient innerRegisteredClient(String clientId) {
    return jdbcOperations
        .queryForStream(
            SELECT_CLIENT_KEY_SQL,
            (rs, rowNum) -> {
              String clientId1 = rs.getString("client_id");
              String clientPublicKey = rs.getString("client_public_key");
              String clientPrivateKey = rs.getString("client_private_key");
              InnerRegisteredClient innerRegisteredClient = new InnerRegisteredClient();
              innerRegisteredClient.setPrivateEncoded(clientPrivateKey);
              innerRegisteredClient.setPublicEncoded(clientPublicKey);
              innerRegisteredClient.setClientId(clientId1);
              return innerRegisteredClient;
            },
            clientId)
        .findFirst()
        .orElse(null);
  }

  public void insertClientKey(InnerRegisteredClient client) {
    SqlParameterValue[] parameters =
        new SqlParameterValue[] {
          new SqlParameterValue(Types.VARCHAR, client.getClientId()),
          new SqlParameterValue(Types.VARCHAR, client.getPublicEncoded()),
          new SqlParameterValue(Types.VARCHAR, client.getPrivateEncoded())
        };
    PreparedStatementSetter pss = new ArgumentPreparedStatementSetter(parameters);
    this.jdbcOperations.update(SAVE_CLIENT_KEY_SQL, pss);
  }
}
