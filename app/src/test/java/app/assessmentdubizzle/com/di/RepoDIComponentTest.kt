package app.assessmentdubizzle.com.di

import app.assessmentdubizzle.com.modules.repository.DubizzleRepository
import kotlinx.coroutines.CoroutineScope
import org.koin.dsl.module


val RepoDIComponentTest = module {

    factory {
            (scope: CoroutineScope) ->
        DubizzleRepository(scope)
    }

}