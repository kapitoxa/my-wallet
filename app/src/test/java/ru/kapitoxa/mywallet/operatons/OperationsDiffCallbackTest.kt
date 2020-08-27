package ru.kapitoxa.mywallet.operatons

import org.junit.Assert
import org.junit.Test
import ru.kapitoxa.mywallet.database.OperationDetail
import ru.kapitoxa.mywallet.operations.OperationsDiffCallback

class OperationsDiffCallbackTest {

    private val operationsList = listOf(
            OperationDetail(
                    1L,
                    "Taxi",
                    1598227200000L,
                    100.0,
                    1L,
                    "Taxi",
                    1L
            ),
            OperationDetail(2L,
                    "Internet",
                    1598227200000L,
                    200.0,
                    2L,
                    "Internet",
                    2L
            )
    )

    private val diffCallback: OperationsDiffCallback = OperationsDiffCallback()

    @Test
    fun `content is the same`() {
        val newItem = OperationDetail(
                1L,
                "Taxi",
                1598227200000L,
                100.0,
                1L,
                "Taxi",
                1L
        )

        Assert.assertTrue(diffCallback.areContentsTheSame(operationsList[0], newItem))
    }

    @Test
    fun `content is different`() {
        val newItem = OperationDetail(
                1L,
                "Metro",
                1598227200000L,
                100.0,
                1L,
                "Taxi",
                1L
        )

        Assert.assertFalse(diffCallback.areContentsTheSame(operationsList[0], newItem))
    }

    @Test
    fun `items is the same`() {
        val operation = OperationDetail(
                1L,
                "Metro",
                1598227200000L,
                100.0,
                1L,
                "Taxi",
                1L
        )

        Assert.assertTrue(diffCallback.areItemsTheSame(operationsList[0], operation))
    }

    @Test
    fun `items is different`() {
        val operation = OperationDetail(2L,
                "Internet",
                1598227200000L,
                200.0,
                2L,
                "Internet",
                2L
        )

        Assert.assertFalse(diffCallback.areItemsTheSame(operationsList[0], operation))
    }
}