package picard.nio;

import com.google.cloud.http.HttpTransportOptions;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.contrib.nio.CloudStorageConfiguration;
import com.google.cloud.storage.contrib.nio.CloudStorageFileSystemProvider;
import shaded.cloud_nio.com.google.api.gax.retrying.RetrySettings;
import shaded.cloud_nio.org.threeten.bp.Duration;

/**
 * Created by farjoun on 11/13/17.
 */
class GoogleStorageUtils {


    public static void initialize() {
        CloudStorageFileSystemProvider.setDefaultCloudStorageConfiguration(GoogleStorageUtils.getCloudStorageConfiguration(20));
        CloudStorageFileSystemProvider.setStorageOptions(GoogleStorageUtils.setGenerousTimeouts(StorageOptions.newBuilder()).build());
    }

    /** The config we want to use. **/
    private static CloudStorageConfiguration getCloudStorageConfiguration(int maxReopens) {
        return CloudStorageConfiguration.builder()
                // if the channel errors out, re-open up to this many times
                .maxChannelReopens(maxReopens)
                .build();
    }

    private static StorageOptions.Builder setGenerousTimeouts(StorageOptions.Builder builder) {
        return builder
                .setTransportOptions(HttpTransportOptions.newBuilder()
                        .setConnectTimeout(120_000)
                        .setReadTimeout(120_000)
                        .build())
                .setRetrySettings(RetrySettings.newBuilder()
                        .setMaxAttempts(15)
                        .setMaxRetryDelay(Duration.ofMillis(256_000L))
                        .setTotalTimeout(Duration.ofMillis(4000_000L))
                        .setInitialRetryDelay(Duration.ofMillis(1000L))
                        .setRetryDelayMultiplier(2.0)
                        .setInitialRpcTimeout(Duration.ofMillis(180_000L))
                        .setRpcTimeoutMultiplier(1.0)
                        .setMaxRpcTimeout(Duration.ofMillis(180_000L))
                        .build());
    }
}
