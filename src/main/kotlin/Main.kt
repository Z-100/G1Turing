import org.jetbrains.kotlinx.dl.api.core.Sequential
import org.jetbrains.kotlinx.dl.api.core.WritingMode
import org.jetbrains.kotlinx.dl.api.core.layer.core.Dense
import org.jetbrains.kotlinx.dl.api.core.layer.core.Input
import org.jetbrains.kotlinx.dl.api.core.layer.reshaping.Flatten
import org.jetbrains.kotlinx.dl.api.core.loss.Losses
import org.jetbrains.kotlinx.dl.api.core.metric.Metrics
import org.jetbrains.kotlinx.dl.api.core.optimizer.Adam
import org.jetbrains.kotlinx.dl.api.inference.TensorFlowInferenceModel
import org.jetbrains.kotlinx.dl.api.summary.printSummary
import org.jetbrains.kotlinx.dl.dataset.OnHeapDataset
import org.jetbrains.kotlinx.dl.dataset.embedded.fashionMnist
import java.io.File

fun main(args: Array<String>) {

    val (train, test) = fashionMnist()

    val stringLabels = mapOf(
        0 to "T-shirt/top",
        1 to "Trousers",
        2 to "Pullover",
        3 to "Dress",
        4 to "Coat",
        5 to "Sandals",
        6 to "Shirt",
        7 to "Sneakers",
        8 to "Bag",
        9 to "Ankle boots",
    )

    train(train, test)

    load(test, stringLabels)
}

private fun train(
    train: OnHeapDataset,
    test: OnHeapDataset
) {


    val model = Sequential.of(
        Input(28, 28, 1),
        Flatten(),
        Dense(300),
        Dense(100),
        Dense(10),
    )

    model.apply {
        compile(
            optimizer = Adam(),
            loss = Losses.SOFT_MAX_CROSS_ENTROPY_WITH_LOGITS,
            metric = Metrics.ACCURACY
        )

        printSummary()

        fit(
            dataset = train,
            epochs = 10,
            batchSize = 100
        )

        val accuracy = evaluate(dataset = test, batchSize = 100).metrics[Metrics.ACCURACY]

        println("Accuracy: $accuracy")
        save(File("model/MNIST-test"), writingMode = WritingMode.OVERRIDE)

    }.close()
}

private fun load(
    test: OnHeapDataset,
    stringLabels: Map<Int, String>
) {
    TensorFlowInferenceModel.load(File("model/MNIST-test")).apply {

        reshape(28, 28, 1)

        val prediction = predict(test.x[0])
        val actual = test.y[0]

        println("Predicted label is: $prediction. This corresponds to class ${stringLabels[prediction]}.")
        println("Actual label is: $actual.")
    }
}
