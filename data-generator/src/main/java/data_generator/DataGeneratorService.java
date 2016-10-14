package data_generator;

import model.GenerationMatrix;

import java.util.List;

interface DataGeneratorService {

    List produceListByMatrix(GenerationMatrix matrix);

}
