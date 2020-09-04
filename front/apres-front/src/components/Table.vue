<template>
    <div class = "table">
        <table class="table">
            <thead>
                <tr>
                    <th v-for="(column, index) in columns" :key="index"> {{column}}</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(item, index) in items" :key="index">
                    <td v-for="(column, indexColumn) in columns" :key="indexColumn">{{item[column]}}</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
export default {
    name: 'Table',
    props: {
        columns: Array,
        items: Array
    }
}
</script>

<style lang="scss" scoped>

</style>