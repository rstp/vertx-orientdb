package org.cstamas.vertx.orientdb.examples.service;

import java.util.List;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ProxyHelper;
import org.cstamas.vertx.orientdb.ConnectionOptions;
import org.cstamas.vertx.orientdb.DocumentDatabase;
import org.cstamas.vertx.orientdb.Manager;

/**
 * OrientDB service, registered whenever a {@link DocumentDatabase} instance is created using {@link
 * Manager#createDocumentInstance(ConnectionOptions, Handler)} method, unregistered when documentInstance closed.
 */
@ProxyGen
public interface DocumentDatabaseService
{
  static DocumentDatabaseService createProxy(Vertx vertx, String address) {
    return ProxyHelper.createProxy(DocumentDatabaseService.class, vertx, address);
  }

  @Fluent
  DocumentDatabaseService insert(String clazz, JsonObject document, Handler<AsyncResult<String>> handler);

  @Fluent
  DocumentDatabaseService delete(String clazz, String where, Handler<AsyncResult<Void>> handler);

  @Fluent
  DocumentDatabaseService select(String clazz, String where, Handler<AsyncResult<List<JsonObject>>> handler);
}
