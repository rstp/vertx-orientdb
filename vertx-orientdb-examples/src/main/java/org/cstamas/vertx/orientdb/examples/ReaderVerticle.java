package org.cstamas.vertx.orientdb.examples;

import java.util.ArrayList;

import com.orientechnologies.orient.core.record.impl.ODocument;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.streams.ReadStream;
import org.cstamas.vertx.orientdb.DocumentDatabase;

/**
 * OrientDB test verticle.
 */
public class ReaderVerticle
    extends AbstractVerticle
{
  private static final Logger log = LoggerFactory.getLogger(ReaderVerticle.class);

  private final DocumentDatabase documentDatabase;

  public ReaderVerticle(final DocumentDatabase documentDatabase) {
    this.documentDatabase = documentDatabase;
  }

  @Override
  public void start(final Future<Void> startFuture) throws Exception {
    vertx.eventBus().consumer("read",
        (Message<JsonObject> m) -> {
          documentDatabase.<ODocument>stream(
              "select from test",
              null,
              astream -> {
                if (astream.succeeded()) {
                  ArrayList<String> arrayList = new ArrayList<>();
                  ReadStream<ODocument> stream = astream.result();
                  stream.endHandler(v -> {
                    log.info("List size=" + arrayList.size());
                  });
                  stream.handler(d -> {
                    arrayList.add(d.field("name"));
                  });
                }
                else {
                  log.error("Stream failed", astream.cause());
                }
              }
          );
        }
    );
    super.start(startFuture);
  }
}
