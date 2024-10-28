package de.larshauke.swapi_android.details.domain.use_case

import de.larshauke.swapi_android.core.models.SearchDto
import de.larshauke.swapi_android.core.models.SwapiType
import de.larshauke.swapi_android.core.models.Url
import de.larshauke.swapi_android.details.domain.repository.DetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface Details {

    operator fun invoke(url: Url, swapiType: SwapiType): Flow<Result<SearchDto>>
}

class DetailsImpl
@Inject
constructor(
    private val repository: DetailsRepository
): Details {

    override fun invoke(url: Url, swapiType: SwapiType): Flow<Result<SearchDto>> =
        flow {
            emit(
                when (swapiType) {
                    SwapiType.FILMS -> repository.getFilmDetails(url)
                    SwapiType.PEOPLE -> repository.getPersonDetails(url)
                    SwapiType.PLANETS -> repository.getPlanetDetails(url)
                    SwapiType.SPECIES -> repository.getSpeciesDetails(url)
                    SwapiType.STARSHIPS -> repository.getStarshipDetails(url)
                    SwapiType.VEHICLES -> repository.getVehicleDetails(url)
                }
            )
        }
            .map { Result.success(it) }
            .catch { Result.failure<Throwable>(it) }

}