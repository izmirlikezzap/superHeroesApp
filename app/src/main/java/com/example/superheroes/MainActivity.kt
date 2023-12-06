package com.example.superheroes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.superheroes.data.Hero
import com.example.superheroes.data.heroes
import com.example.superheroes.ui.theme.SuperHeroesAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuperHeroesAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HeroApp()
                }
            }
        }
    }
}


@Composable
fun HeroApp() {
    Scaffold (
        topBar = {
            SuperHeroesTopBar()
        }
    ){
        it ->
        LazyColumn(contentPadding = it){
            items(heroes){
                HeroItem(hero = it,
                    modifier = Modifier.padding(5.dp)
                        )
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp) )

            }
        }
    }
}


@Composable
fun HeroItem(hero: Hero, modifier: Modifier = Modifier){
    var expanded by remember { mutableStateOf(false) }
    var isFavorite by remember { mutableStateOf(false) }
    val color by animateColorAsState(targetValue = if (expanded) MaterialTheme.colorScheme.tertiaryContainer
        else MaterialTheme.colorScheme.primaryContainer,label = "",

    )
    Card(modifier=Modifier.padding(start = 10.dp,end= 10.dp)) {

        Column(
            modifier = modifier

                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(modifier=modifier.fillMaxWidth()) {
                HeroIcon(heroIcon = hero.image, modifier = Modifier.width(64.dp))
                HeroInformation(heroName =  hero.name , heroDesc = hero.description)
                Spacer(modifier = Modifier.weight(1f))
                HeroItemButton(expanded = expanded, onClick = { expanded = !expanded })

                HeroFavoriteButton(isFavorite=isFavorite, onToggle = { isFavorite = it })
            }
            if (expanded) {
                HeroSpec(
                    hero.spec,  modifier = Modifier.padding(
                        start = dimensionResource(id = R.dimen.padding_medium),
                        top = dimensionResource(R.dimen.padding_small),
                        end = dimensionResource(R.dimen.padding_medium),
                        bottom = dimensionResource(R.dimen.padding_medium)
                    )
                )

            }

        }
    }
}
@Composable
fun SuperHeroesTopBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(title = {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom, // Aligns the content to the bottom
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.superheroes_topbar),
                contentDescription = null,
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp)
            )

        }
    })
}

@Composable
fun HeroFavoriteButton(
    isFavorite: Boolean,
    onToggle: (Boolean) -> Unit,
    color: Color = Color.Red
) {
    IconToggleButton(
        checked = isFavorite,
        onCheckedChange = {
            onToggle(it)
        }
    ) {
        Icon(
            tint = color,
            imageVector = if (isFavorite) {
                Icons.Filled.Favorite
            } else {
                Icons.Default.FavoriteBorder
            },
            contentDescription = null
        )
    }
}



@Composable
private fun HeroItemButton(

    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
){
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary
        )
    }

}

@Composable
fun HeroSpec(
    @StringRes heroSpec: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.about),
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(heroSpec),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Composable
fun HeroIcon(
    @DrawableRes heroIcon: Int,
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier
            .size(64.dp)
            .padding(all = 8.dp)
            .clip(MaterialTheme.shapes.small),
        contentScale = ContentScale.Crop,
        painter = painterResource(heroIcon),
        contentDescription = null
    )
}


@Composable
fun HeroInformation(
    @StringRes heroName: Int,
    @StringRes heroDesc: Int,
    modifier: Modifier = Modifier
) {
    Column {
        Text(
            text = stringResource(heroName),
            style = MaterialTheme.typography.labelLarge,
            modifier = modifier.padding(top = 8.dp)
        )
        Text(
            text = stringResource(heroDesc),
            style = MaterialTheme.typography.bodySmall,
            modifier = modifier.fillMaxWidth(0.7f)
        )
    }
}
