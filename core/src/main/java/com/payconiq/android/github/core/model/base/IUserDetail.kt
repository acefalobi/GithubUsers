package com.payconiq.android.github.core.model.base

/**
 * Base interface for detailed user models.
 */
interface IUserDetail : IUser {
    /**
     * The name of the user.
     */
    val name: String?
    /**
     * The company the user is affiliated with
     */
    val company: String?

    /**
     * Where the user resides.
     */
    val location: String?

    /**
     * A short bio of the user.
     */
    val bio: String?

    /**
     * The no of repositories the user owns.
     */
    val noOfRepos: Int

    /**
     * The no of Github Gists the user owns.
     */
    val noOfGists: Int

    /**
     * The number of users following the user.
     */
    val noOfFollowers: Int

    /**
     * The number of users the user is following.
     */
    val noOfFollowing: Int
}
