# Presentation Sample Archive

Android Application 의 Presentation Layer(UI Layer) 에 대한 샘플 코드 모음 Repository 입니다. 

## Navigation

화면 간 이동을 구현하는 Navigation 코드 모음

### Activity <-> Activity
- See [SourceActivity](https://github.com/cocoslime/Android-Navigation-Sample/blob/master/app/src/main/java/com/cocoslime/presentation/navigation/activity/SourceNavActivity.kt) 
and [DestinationActivity](https://github.com/cocoslime/Android-Navigation-Sample/blob/master/app/src/main/java/com/cocoslime/presentation/navigation/activity/DestinationActivity.kt) code.
- [Get a result from an activity](https://developer.android.com/training/basics/intents/result).

### [Navigate between Composable](https://developer.android.com/develop/ui/compose/navigation)
- See [navigation.compose package](https://github.com/cocoslime/Android-Navigation-Sample/blob/master/app/src/main/java/com/cocoslime/presentation/navigation/compose) code.
- Compose Navigation 에서 Destination 간에 데이터 전달: [Type Safe Navigation](https://developer.android.com/guide/navigation/design/type-safety)
- Type-Safe Navigation

### Fragment -> Fragment
- See [navigation.fragment package](https://github.com/cocoslime/Android-Navigation-Sample/blob/master/app/src/main/java/com/cocoslime/presentation/navigation/fragment) code.
- [Fragments and the Kotlin DSL](https://developer.android.com/guide/navigation/design/kotlin-dsl#navigate)
- Type-Safe Navigation

## RecyclerView
- [recyclerview package](https://github.com/cocoslime/Android-Navigation-Sample/blob/master/app/src/main/java/com/cocoslime/presentation/recyclerview)
- ListAdapter and DiffUtil
- [BindingViewHolder](https://github.com/cocoslime/Android-Navigation-Sample/blob/master/app/src/main/java/com/cocoslime/presentation/recyclerview/BindingViewHolder.kt) 을 통한 보일러 플레이트 코드 제거. (ViewHolder with ViewBinding 기반) 

## Architecture
### MVI with [Orbit](https://orbit-mvi.org/)


## Dependencies
- Navigation
- Jetpack Compose
- Hilt



