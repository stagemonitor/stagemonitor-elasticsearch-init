package org.stagemonitor.init;

import org.stagemonitor.configuration.source.ConfigurationSource;
import org.stagemonitor.configuration.source.SimpleSource;
import org.stagemonitor.core.CorePlugin;
import org.stagemonitor.core.Stagemonitor;
import org.stagemonitor.core.StagemonitorConfigurationSourceInitializer;
import org.stagemonitor.core.elasticsearch.ElasticsearchClient;

public class InitElasticsearch extends StagemonitorConfigurationSourceInitializer {

    private static ConfigurationSource argsConfigurationSource = new SimpleSource("Process Arguments");

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            throw new IllegalArgumentException("Please specify the elasticsearch url");
        }
        argsConfigurationSource.save("stagemonitor.reporting.elasticsearch.url", args[0]);
        Stagemonitor.init();
        final ElasticsearchClient elasticsearchClient = Stagemonitor.getConfiguration().getConfig(CorePlugin.class).getElasticsearchClient();
        elasticsearchClient.checkEsAvailability();
        elasticsearchClient.waitForCompletion();
        if (elasticsearchClient.isElasticsearchAvailable()) {
            System.out.println("SUCCESS: Elasticsearch indices initialized, Kibana dashboards imported");
        } else {
            System.err.println("ERROR: Elasticsearch is not available");
        }
    }

    @Override
    public void modifyConfigurationSources(ModifyArguments modifyArguments) {
        modifyArguments.addConfigurationSourceAsFirst(argsConfigurationSource);
    }
}