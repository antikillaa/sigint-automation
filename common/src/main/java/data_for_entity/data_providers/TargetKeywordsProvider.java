package data_for_entity.data_providers;

import utils.RandomGenerator;

public class TargetKeywordsProvider extends DependencyDataProvider {
    
    @Override
    public Object generate(int length) {
        DependencyData dependencyData = getDependencyData();
        int keywordsRatio = Integer.parseInt((String)dependencyData.getData("keywordRatio"));
        return RandomGenerator.generateKeywords(3, keywordsRatio);
    }
}
