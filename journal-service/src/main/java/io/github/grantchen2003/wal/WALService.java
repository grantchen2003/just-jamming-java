package io.github.grantchen2003.wal;

import com.google.protobuf.Empty;
import io.github.grantchen2003.wal.util.FileUtils;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

public class WALService extends WALGrpc.WALImplBase{
    private static final Path walsDir = Path.of("wals");
    private static final String charsetName = "UTF-8";

    @Override
    public void create(Empty request, StreamObserver<CreateResponse> responseObserver) {
        final String journalId = UUID.randomUUID().toString();
        final Path journalPath = walsDir.resolve(journalId + ".bin");

        try {
            FileUtils.createDirectoryIfNotExist(walsDir);
            FileUtils.createFileIfNotExists(journalPath);
        } catch (IOException e) {
            responseObserver.onError(
                    Status.INTERNAL.withDescription("Failed to create journal").asRuntimeException()
            );
            return;
        }

        final CreateResponse response = CreateResponse.newBuilder().setWalId(journalId).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void append(AppendRequest request, StreamObserver<Empty> responseObserver) {
        final String journalId = request.getWalId();
        final String data = request.getData();

        try {
            final byte[] byteData = data.getBytes(charsetName);

            if (byteData.length > 255) {
                throw new IllegalArgumentException("data is too long");
            }

            byte[] entry = new byte[1 + byteData.length];
            entry[0] = (byte) byteData.length;
            System.arraycopy(byteData, 0, entry, 1, byteData.length);

            final Path journalPath = walsDir.resolve(journalId + ".bin");
            FileUtils.appendBytesToFile(journalPath, entry);

        } catch (IllegalArgumentException | IOException e) {
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Failed to append: " + e.getMessage())
                    .asRuntimeException());
            return;
        }

        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }
}
